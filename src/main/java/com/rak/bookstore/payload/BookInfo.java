package com.rak.bookstore.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

/**
 * BookInfo
 */
@Validated
public @Data class BookInfo   {

  @ApiModelProperty(value = "id")
  private Long id = null;

  @ApiModelProperty(value = "Name of book")
  @NotNull
  private String name = null;

  private String category = null;

  @ApiModelProperty(value = "Book description")
  private String description = null;

  @ApiModelProperty(value = "Author Name")
  @NotNull
  private String authorName = null;

  @ApiModelProperty(example = "user@example.com", value = "Author Email Address")
  @Email
  @NotNull
  private String authorEmailAddress = null;

  @ApiModelProperty(value = "price of the book")
  @NotNull
  private Double price = null;

  @ApiModelProperty(value = "ISBN")
  @NotNull
  private String isbn = null;

  /**
   * Book classification
   */
  public  enum ClassificationEnum {
    FICTION("FICTION"),
    
    COMIC("COMIC"),
    
    ADVENTURE("ADVENTURE"),
    
    TRAVEL("TRAVEL"),
    
    HEALTH("HEALTH"),
    
    MYSTERY("MYSTERY");

    private String value;

    ClassificationEnum(String value) {
      this.value = value;
    }


    @JsonCreator
    public static ClassificationEnum fromValue(String text) {
      for (ClassificationEnum b : ClassificationEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @ApiModelProperty(value = "Book classification")
  @NotNull(message = "Book classification is mandatory")
  private ClassificationEnum classification = null;

}

