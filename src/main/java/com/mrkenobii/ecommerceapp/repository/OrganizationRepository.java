package com.mrkenobii.ecommerceapp.repository;

import com.mrkenobii.ecommerceapp.model.Organization;
import com.mrkenobii.ecommerceapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    Organization findByDomain(String domain);
}
