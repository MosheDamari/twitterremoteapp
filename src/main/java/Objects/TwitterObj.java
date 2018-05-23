package Objects;

public class TwitterObj
{
  String name;
  String title;
  String link;
  String content;
  String image;
  String disc;


  public TwitterObj()
    {
        this.content = "";
        this.name = "";
        this.title = "";
        this.link = "";
        this.image = "";
        this.disc = "";
    }

    public TwitterObj(String name, String link, String content, String image, String title , String disc)
  {
      this.content = content;
      this.name = name;
      this.title = title;
      this.link = link;
      this.image = image;
      this.disc = disc;

  }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getContent() {
        return content;
    }

    public String getImage() {
        return image;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public void PrintTwitterObj()
    {
        System.out.println("UserName is: " + name + "\n" + "The Link:" +  link + "\n" + "The website Content is: " + content + "\n" + "The website title is: " + title + "\n"+ "The website Description  is: " + disc + "\n");
    }
}
