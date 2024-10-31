package com.shadi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserImageUploadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mobileNumber;

    @Lob
    private byte[] image1;

    @Lob
    private byte[] image2;

    @Lob
    private byte[] image3;

    @Lob
    private byte[] image4;

    @Lob
    private byte[] image5;

    @Lob
    private byte[] image6;

    @Lob
    private byte[] image7;

    @Lob
    private byte[] image8;

    @Lob
    private byte[] image9;

    @Lob
    private byte[] image10;

    public void setImage(int index, byte[] image) {
        switch (index) {
            case 1 -> this.image1 = image;
            case 2 -> this.image2 = image;
            case 3 -> this.image3 = image;
            case 4 -> this.image4 = image;
            case 5 -> this.image5 = image;
            case 6 -> this.image6 = image;
            case 7 -> this.image7 = image;
            case 8 -> this.image8 = image;
            case 9 -> this.image9 = image;
            case 10 -> this.image10 = image;
            default -> throw new IllegalArgumentException("Invalid image index: " + index);
        }
    }
}
