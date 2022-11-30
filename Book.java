class Book {
    private String id,Author, Title, Genre, Publish_Date, Description, Price;

    public void set_id(String id) {
        this.id = id;
    }
    public void set_Author(String name) {
        this.Author = name;
    }
    public void set_Title(String title) {
        this.Title = title;
    }
    public void set_Genre(String Genre) {
        this.Genre = Genre;
    }
    public void set_Publish_Date(String date) {
        this.Publish_Date = date;
    }
    public void set_Description(String desc) {
        this.Description = desc;
    }
    public void set_Price(String price) {
        this.Price = price;
    }

    public String get_id() {
        return this.id;
    }
    public String get_Author() {
        return this.Author;
    }
    public String get_Title() {
        return this.Title;
    }
    public String get_Genre() {
        return this.Genre;
    }
    public String get_Publish_Date() {
        return this.Publish_Date;
    }
    public String get_Description() {
        return this.Description;
    }
    public String get_Price() {
        return this.Price;
    }
    public String toString(){
        return  "\nAuthor Name: "+ this.Author +
                "\n=========================================="+
                "\nTitle: "+ this.Title +
                "\nGenre: "+ this.Genre+
                "\nPublish_Date: "+ this.Publish_Date+
                "\nPrice: "+ this.Price+
                "\nDescription: " + this.Description;
    }

}