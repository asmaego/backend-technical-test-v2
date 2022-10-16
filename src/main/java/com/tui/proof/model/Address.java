package com.tui.proof.model;

import lombok.*;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "addresses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Address {
    @Id
    @NonNull
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String street;

    private String postcode;

    private String city;

    private String country;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "deliveryAddress", cascade = CascadeType.ALL)
    private List<Order> orders;
}
