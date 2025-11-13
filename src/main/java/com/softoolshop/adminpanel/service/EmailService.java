package com.softoolshop.adminpanel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.softoolshop.adminpanel.ProjectConstant;
import com.softoolshop.adminpanel.dto.CreateOrderDTO;
import com.softoolshop.adminpanel.dto.OrderDetailDTO;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	public void sendSimpleEmail(String toEmail, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("admin@softoolshop.in"); // Must match spring.mail.username
		message.setTo(toEmail);
		message.setSubject(subject);
		message.setText(body);
		message.setBcc("softoolshop@gmail.com");

		mailSender.send(message);
	}

	private void sendEmailAsHtml(String toEmail, String subject, String body) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom("admin@softoolshop.in");
			helper.setTo(toEmail);
			helper.setSubject(subject);
			helper.setText(body, true); // <--- true enables HTML
			helper.setBcc("softoolshop@gmail.com");

			mailSender.send(message);
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("Exception in sending email "+e.getMessage());
		}
	}

//	public void sendOrdrConfermationMail(CreateOrderDTO ordrDto) {
//		String toEmail = ordrDto.getEmail();
//		String sub = ProjectConstant.SUB_PUR_ORDR + ordrDto.getOrderId();
//		String body = ProjectConstant.BODY_PUR_ORDR.replace("@CUST_NAME@", ordrDto.getName()).replace("@ORDR_ID@",
//				ordrDto.getOrderId() + " ");
//		System.out.println("Sending mail to : " + ordrDto.getEmail() + " order_id=" + ordrDto.getOrderId());
//		this.sendSimpleEmail(toEmail, sub, body);
//	}

	public void sendOrdrConfermationMail(CreateOrderDTO order) {
		String sub = ProjectConstant.SUB_PUR_ORDR + order.getOrderId();
		StringBuilder tableRows = new StringBuilder();
		double total = 0;

		for (OrderDetailDTO detail : order.getOrderDetails()) {
		    double itemTotal = detail.getPrice().doubleValue() * detail.getQuantity();
		    total += itemTotal;

		    tableRows.append("<tr>")
		        .append("<td>").append(detail.getTitle()).append("</td>")
		        //.append("<td>").append("www.softoolshop.in/product/"+detail.getProductId()).append("</td>")
		        .append("<td>₹").append(detail.getPrice()).append("</td>")
		        .append("<td>").append(detail.getQuantity()).append("</td>")
		        .append("<td>₹").append(String.format("%.2f", itemTotal)).append("</td>")
		        .append("</tr>");
		}
		
		String htmlBody = "<h2>Hi "+order.getName()+",</h2><br />"
				+ "<p>Thank you for your purchase from Softool Shop!. "
				+ "Below are the details of order:</p>"
				+ "<table border=\"1\" cellpadding=\"8\" cellspacing=\"0\" style=\"border-collapse: collapse; width: 100%;\">\r\n"
				+ "        <thead>\r\n"
				+ "            <tr style=\"background-color: #f2f2f2;\">\r\n"
				+ "                <th>Product</th>\r\n"
				//+ "                <th>Dowload Link</th>\r\n"
				+ "                <th>Price(₹)</th>\r\n"
				+ "                <th>Quantity</th>\r\n"
				+ "                <th>Total</th>\r\n"
				+ "            </tr>\r\n"
				+ "        </thead>"
				+ "		   <tbody>"
				+ "        "+tableRows.toString()+""
				+ "		   </tbody>"
				+ "		   <tfoot>\r\n"
				+ "            <tr style=\"font-weight: bold;\">\r\n"
				+ "                <td colspan=\"3\" align=\"right\">Total Amount:</td>\r\n"
				+ "                <td>"+total+"</td>\r\n"
				+ "            </tr>\r\n"
				+ "        </tfoot>"
				+ "</table><br />"
				+ "<p>If you have any questions, reply to this email.</p>"
				+ "<p><strong>Best regards, Softool Team</strong></p><br />";
		
		htmlBody += "📧 admin@softoolshop.in<br />" +
				 	"🌐 https://softoolshop.in";
		
		this.sendEmailAsHtml(order.getEmail(), sub, htmlBody);

	}
}
