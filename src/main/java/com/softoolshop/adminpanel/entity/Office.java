package com.softoolshop.adminpanel.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "office")
@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.ToString
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "office_id")
    private Integer officeId;

    @Column(name = "address_line1", length = 255)
    private String addressLine1;

    @Column(name = "address_line2", length = 255)
    private String addressLine2;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "primary_phone", length = 20)
    private String primaryPhone;

    @Column(name = "primary_email", length = 100)
    private String primaryEmail;

    @Column(name = "secondary_phone", length = 20)
    private String secondaryPhone;

    @Column(name = "secondary_email", length = 100)
    private String secondaryEmail;
}

