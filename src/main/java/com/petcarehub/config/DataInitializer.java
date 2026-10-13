package com.petcarehub.config;

import com.petcarehub.entity.Role;
import com.petcarehub.entity.ServiceCategory;
import com.petcarehub.entity.ServiceEntity;
import com.petcarehub.entity.User;
import com.petcarehub.repository.ServiceRepository;
import com.petcarehub.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, ServiceRepository serviceRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Seed Initial System & Role Accounts (+91 Indian phone format)
        User admin = seedUserIfNotExists("admin@petcarehub.com", "admin123", "Platform Administrator", "+91-98765-43210", Role.ROLE_ADMIN, "PetCareHub India HQ, Bengaluru", null, "System Administration");
        User vet = seedUserIfNotExists("vet@petcarehub.com", "vet123", "Dr. Sarah Jenkins, DVM", "+91-98234-56789", Role.ROLE_VETERINARIAN, "104 Healthcare Blvd, Mumbai", "Apex Veterinary Hospital", "Small Animal Surgery & Diagnostics");
        User provider = seedUserIfNotExists("groomer@petcarehub.com", "groomer123", "Paws & Spa Studio", "+91-98123-45678", Role.ROLE_SERVICE_PROVIDER, "45 Pet Plaza Way, Delhi", "Paws & Spa Studio", "Hydrotherapy & Canine Styling");
        seedUserIfNotExists("owner@petcarehub.com", "owner123", "Alex Morgan", "+91-98901-23456", Role.ROLE_PET_OWNER, "742 MG Road, Bengaluru", null, null);

        // Seed Core Care Services Catalog if empty (Prices in Indian Rupees ₹)
        if (serviceRepository.count() == 0) {
            serviceRepository.save(new ServiceEntity("Comprehensive Veterinary Checkup", ServiceCategory.VETERINARY, "Full physical examination, vitals assessment, heart and lung auscultation, and dental check.", 750.0, 30, vet));
            serviceRepository.save(new ServiceEntity("Rabies & DHPP Immunization", ServiceCategory.VETERINARY, "Standard core vaccination series including documentation and digital pet vaccine passport certificate.", 850.0, 20, vet));
            serviceRepository.save(new ServiceEntity("Ultrasonic Dental Scaling & Polish", ServiceCategory.DENTAL, "Removal of plaque and calculus, subgingival curettage, and enamel polishing.", 2400.0, 60, vet));
            serviceRepository.save(new ServiceEntity("Full Luxury Grooming & Coat De-shedding", ServiceCategory.GROOMING, "Warm hydro-massage bath, hypoallergenic shampoo, blow dry, nail trim, ear cleaning, and styling.", 1500.0, 60, provider));
            serviceRepository.save(new ServiceEntity("Medicated Flea & Tick Treatment Bath", ServiceCategory.GROOMING, "Gentle skin soothing bath with veterinary-grade anti-parasitic treatment.", 950.0, 45, provider));
            serviceRepository.save(new ServiceEntity("Climate-Controlled Overnight Suite Boarding", ServiceCategory.BOARDING, "Deluxe cozy suite, 4 daily outdoor play sessions, continuous supervision and dietary regimen.", 1200.0, 1440, provider));
            serviceRepository.save(new ServiceEntity("Puppy & Canine Behavioral Training", ServiceCategory.TRAINING, "One-on-one session with certified trainer focusing on leash manners, recall, and socialization.", 1800.0, 45, provider));
        }
    }

    private User seedUserIfNotExists(String email, String rawPassword, String fullName, String phone, Role role, String address, String clinic, String spec) {
        return userRepository.findByEmail(email).orElseGet(() -> {
            User user = new User(
                    email,
                    passwordEncoder.encode(rawPassword),
                    fullName,
                    phone,
                    role,
                    address,
                    clinic,
                    spec
            );
            return userRepository.save(user);
        });
    }
}
