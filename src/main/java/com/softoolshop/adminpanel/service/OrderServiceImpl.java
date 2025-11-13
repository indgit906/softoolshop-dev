package com.softoolshop.adminpanel.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softoolshop.adminpanel.dto.CreateOrderDTO;
import com.softoolshop.adminpanel.dto.OrderDetailDTO;
import com.softoolshop.adminpanel.dto.OrderSearchRequest;
import com.softoolshop.adminpanel.dto.ProductDTO;
import com.softoolshop.adminpanel.entity.Order;
import com.softoolshop.adminpanel.entity.OrderDetail;
import com.softoolshop.adminpanel.entity.OrderStatus;
import com.softoolshop.adminpanel.entity.Payment;
import com.softoolshop.adminpanel.entity.Payment.PaymentStatus;
import com.softoolshop.adminpanel.entity.Product;
import com.softoolshop.adminpanel.entity.Customer;
import com.softoolshop.adminpanel.repository.OrderRepository;
import com.softoolshop.adminpanel.repository.PaymentRepository;
import com.softoolshop.adminpanel.repository.ProductRepository;
import com.softoolshop.adminpanel.repository.CustomerRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private CustomerRepository custRepo;
	@Autowired
	private PaymentRepository pymntRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	@Transactional
	public CreateOrderDTO createOrder(CreateOrderDTO dto) {

		Long custId = custRepo.getCustomerId(dto.getEmail());

		// Get or create user
		if (custId == null) {
			Customer newUser = new Customer();
			newUser.setFirstName(dto.getFirstName());
			newUser.setLastName(dto.getLastName());
			newUser.setEmail(dto.getEmail());
			newUser.setPhone(dto.getPhone());

			Customer savedUser = custRepo.save(newUser);
			custId = savedUser.getCustId();
		}

		dto.setCustId(custId);

		Order order = new Order();
		order.setCustId(dto.getCustId());
		order.setName(dto.getFirstName() + " " + dto.getLastName());
		order.setPhone(dto.getPhone());
		order.setEmail(dto.getEmail());

		double totalAmt = 0.0;
		for (OrderDetailDTO detailDto : dto.getOrderDetails()) {
			OrderDetail detail = new OrderDetail();
			// set Product id
			detail.setProduct(new Product(detailDto.getProductId()));
			// detail.setProductName(detailDto.getProductName());
			totalAmt += detailDto.getPrice().doubleValue() * detailDto.getQuantity();
			detail.setPrice(detailDto.getPrice());
			detail.setQuantity(detailDto.getQuantity());
			detail.setOrder(order);
			order.addOrderDetail(detail); // auto-sets bidirectional relation
		}
		Payment pymntEntity = new Payment();
		pymntEntity.setOrder(order);
		pymntEntity.setAmount(BigDecimal.valueOf(totalAmt));
		pymntEntity.setPaymentMethod("UPI");
		// pymntEntity.setPaymentStatus(Payment.PaymentStatus.PENDING);
		pymntEntity.setTransactionRef(dto.getTransactionRef());
		order.setPayment(pymntEntity);

		Order frmDb = orderRepo.save(order);
		
		
		modelMapper.getConfiguration()
		    .setAmbiguityIgnored(true)
		    .setSkipNullEnabled(true)
		    .setFieldMatchingEnabled(true);

		TypeMap<OrderDetail, OrderDetailDTO> typeMap = modelMapper.getTypeMap(OrderDetail.class, OrderDetailDTO.class);
		if (typeMap == null) {
		    typeMap = modelMapper.createTypeMap(OrderDetail.class, OrderDetailDTO.class);
		}

		typeMap.addMappings(mapper -> {
		    mapper.map(src -> src.getProduct().getProductId(), OrderDetailDTO::setProductId);
		    mapper.map(src -> src.getProduct().getCategoryId(), OrderDetailDTO::setCategoryId);
		    mapper.map(src -> src.getProduct().getTitle(), OrderDetailDTO::setTitle);
		});

		CreateOrderDTO orderCreated = modelMapper.map(frmDb, CreateOrderDTO.class);
		orderCreated.setTotalAmt(totalAmt);
		return orderCreated;


	}

	@Override
	public List<CreateOrderDTO> getAllOrders() {
		List<Order> frmDb = orderRepo.findAll();
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		modelMapper.typeMap(OrderDetail.class, OrderDetailDTO.class).addMappings(mapper -> {
			mapper.map(src -> src.getProduct().getProductId(), OrderDetailDTO::setProductId);
			mapper.map(src -> src.getProduct().getCategoryId(), OrderDetailDTO::setCategoryId);
			mapper.map(src -> src.getProduct().getTitle(), OrderDetailDTO::setTitle);
		});
		modelMapper.typeMap(Order.class, CreateOrderDTO.class).addMappings(mapper -> {
			mapper.map(src -> src.getPayment().getTransactionRef(), CreateOrderDTO::setTransactionRef);
		});
		List<CreateOrderDTO> orderDTOs = Arrays.asList(modelMapper.map(frmDb, CreateOrderDTO[].class));

		orderDTOs.forEach(orderDto -> {
			double total = Optional.ofNullable(orderDto.getOrderDetails()).orElse(Collections.emptyList()).stream()

					.mapToDouble(detail -> Optional.ofNullable(detail.getPrice()).orElse(BigDecimal.ZERO).doubleValue()
							* detail.getQuantity())
					.sum();
			orderDto.setTotalAmt(total);
		});
		return orderDTOs;
	}

	@Override
	@Transactional
	public CreateOrderDTO completeOrder(CreateOrderDTO order) {

		Optional<Order> optOrdr = orderRepo.findById(order.getOrderId());
		if (optOrdr.isPresent()) {
			Order ordrEntity = optOrdr.get();
			if (order.getOrderStatus().equalsIgnoreCase("FULFILLED")) {
				ordrEntity.setOrderStatus(OrderStatus.FULFILLED);
				ordrEntity.setPaidAt(new Timestamp(System.currentTimeMillis()));
				pymntRepo.updatePaymentStatusAndVerification(PaymentStatus.PAID, order.getOrderId());
			} else if (order.getOrderStatus().equalsIgnoreCase("CANCELLED")) {
				ordrEntity.setOrderStatus(OrderStatus.CANCELLED);
			} else {
				ordrEntity.setOrderStatus(OrderStatus.PLACED);
			}
			Order updatedEntity = orderRepo.save(ordrEntity);
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			modelMapper.typeMap(OrderDetail.class, OrderDetailDTO.class).addMappings(mapper -> {
				mapper.map(src -> src.getProduct().getProductId(), OrderDetailDTO::setProductId);
				mapper.map(src -> src.getProduct().getCategoryId(), OrderDetailDTO::setCategoryId);
				mapper.map(src -> src.getProduct().getTitle(), OrderDetailDTO::setTitle);
			});
			order = modelMapper.map(updatedEntity, CreateOrderDTO.class);
		}

		return order;
	}

	@Override
	public CreateOrderDTO getOrderById(Long orderId) {
		Order savedObj = orderRepo.getByOrderId(orderId);

		CreateOrderDTO ordrdto = new CreateOrderDTO();
		ordrdto.setOrderId(savedObj.getOrderId());
		ordrdto.setName(savedObj.getName());
		ordrdto.setEmail(savedObj.getEmail());
		ordrdto.setPhone(savedObj.getPhone());
		ordrdto.setOrderStatus(savedObj.getOrderStatus().name());
		//ordrdto.setTotalAmt(totalAmt);
		
		List<OrderDetailDTO> listOrdrDtlDto = new ArrayList<>();
		double totalAmt = 0.0;
		for (OrderDetail ordrDtlEntity : savedObj.getOrderDetails()) {
			OrderDetailDTO odrdDtlDto = new OrderDetailDTO();
			odrdDtlDto.setOrderDtlId(ordrDtlEntity.getOrderDtlId());
			odrdDtlDto.setPrice(ordrDtlEntity.getPrice());
			odrdDtlDto.setQuantity(ordrDtlEntity.getQuantity());
			totalAmt += ordrDtlEntity.getPrice().doubleValue() * ordrDtlEntity.getQuantity();
			Product prd = ordrDtlEntity.getProduct();
			System.out.println(prd.getProductId()+"\t"+prd.getCategoryId()+"\t"+prd.getTitle());
			odrdDtlDto.setProductId(prd.getProductId());
			odrdDtlDto.setCategoryId(prd.getCategoryId());
			odrdDtlDto.setTitle(prd.getTitle());
			listOrdrDtlDto.add(odrdDtlDto);
		}
		ordrdto.setTotalAmt(totalAmt);
		ordrdto.setOrderDetails(listOrdrDtlDto);
		return ordrdto;
	}

	@Override
	public List<CreateOrderDTO> searchOrder(OrderSearchRequest searchReq) {
		OrderStatus status = null;
		if (searchReq.getOrderStatus() != null && !searchReq.getOrderStatus().isBlank()) {
		    status = OrderStatus.valueOf(searchReq.getOrderStatus().toUpperCase());
		}
		List<Order> ordrEntities = orderRepo.searchOrders(searchReq.getSearchKey(), status);
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		modelMapper.typeMap(OrderDetail.class, OrderDetailDTO.class).addMappings(mapper -> {
			mapper.map(src -> src.getProduct().getProductId(), OrderDetailDTO::setProductId);
			mapper.map(src -> src.getProduct().getCategoryId(), OrderDetailDTO::setCategoryId);
			mapper.map(src -> src.getProduct().getTitle(), OrderDetailDTO::setTitle);
		});
		modelMapper.typeMap(Order.class, CreateOrderDTO.class).addMappings(mapper -> {
			mapper.map(src -> src.getPayment().getTransactionRef(), CreateOrderDTO::setTransactionRef);
		});
		List<CreateOrderDTO> orderDTOs = Arrays.asList(modelMapper.map(ordrEntities, CreateOrderDTO[].class));

		orderDTOs.forEach(orderDto -> {
			double total = Optional.ofNullable(orderDto.getOrderDetails()).orElse(Collections.emptyList()).stream()

					.mapToDouble(detail -> Optional.ofNullable(detail.getPrice()).orElse(BigDecimal.ZERO).doubleValue()
							* detail.getQuantity())
					.sum();
			orderDto.setTotalAmt(total);
		});
		return orderDTOs;
	}

}
