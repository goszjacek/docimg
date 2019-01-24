public class PdfImage {
    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public PdfImage(String description, String url) {
        this.description = description;
        this.url = url;
    }

    String description,url;

}
