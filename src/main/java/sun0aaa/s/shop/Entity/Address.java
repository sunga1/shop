package sun0aaa.s.shop.Entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {
    private String zipcode;
    private String streetAddress;
    private String detailAddress;

    public Address() {
    }

    public Address(String zipcode, String streetAddress, String detailAddress) {
        this.zipcode = zipcode;
        this.streetAddress = streetAddress;
        this.detailAddress = detailAddress;
    }
}
