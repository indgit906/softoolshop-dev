package com.softoolshop.adminpanel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softoolshop.adminpanel.entity.ContactForm;

public interface ContactFormRepository extends JpaRepository<ContactForm, Long> {
}
