package lay.learn.springbestpractice.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.NoArgsConstructor;
/*
*   https://github.com/querydsl/querydsl/issues/3365
* */
@Embeddable
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Address {
    String city;
    String street;
    String zipcode;

    @Builder
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

}
