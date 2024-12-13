package vttp.batch5.ssf.noticeboard.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Notice {

    private String id;

    @NotEmpty(message = "field is mandatory")
    @Size(min = 3, max = 128, message = "Title must be inbetween 3 and 128 characters")
    private String title;

    @NotEmpty(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String poster;

    @NotNull(message = "field is mandatory")
    @Future(message = "Future dates only")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date postDate;

    @NotEmpty(message = "Please select at least 1 category")
    @Size(min=1, message = "Minimum 1 category")
    private List<String> categories; // change to List<String>?

    @NotEmpty(message = "field is mandatory")
    private String text;


    public Notice() {

    }
    
    
    public Notice(
            @NotEmpty(message = "field is mandatory") @Size(min = 3, max = 128, message = "Title must be inbetween 3 and 128 characters") String title,
            @NotEmpty(message = "Email is required") @Email(message = "Please provide a valid email address") String poster,
            @NotNull(message = "field is mandatory") @Future(message = "Future dates only") Date postDate,
            @NotEmpty(message = "Please select at least 1 category") @Size(min = 1, message = "Minimum 1 category") List<String> categories,
            @NotEmpty(message = "field is mandatory") String text) {
        this.title = title;
        this.poster = poster;
        this.postDate = postDate;
        this.categories = categories;
        this.text = text;
    }







    public Notice(String id,
            @NotEmpty(message = "field is mandatory") @Size(min = 3, max = 128, message = "Title must be inbetween 3 and 128 characters") String title,
            @NotEmpty(message = "Email is required") @Email(message = "Please provide a valid email address") String poster,
            @NotNull(message = "field is mandatory") @Future(message = "Future dates only") Date postDate,
            @NotEmpty(message = "Please select at least 1 category") @Size(min = 1, message = "Minimum 1 category") List<String> categories,
            @NotEmpty(message = "field is mandatory") String text) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.postDate = postDate;
        this.categories = categories;
        this.text = text;
    }







    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPoster() {
        return poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }
    public Date getPostDate() {
        return postDate;
    }
    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
   
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public List<String> getCategories() {
        return categories;
    }
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    
    
    
    
}

