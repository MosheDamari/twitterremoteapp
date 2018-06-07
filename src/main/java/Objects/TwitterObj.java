package Objects;

import org.json.JSONObject;

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
    public JSONObject ToJson()
    {
        JSONObject obj = new JSONObject();
        obj.append("UserName",this.name);
        obj.append("Link",this.link);
        obj.append("Content",this.content);
        obj.append("title",this.title);
        obj.append("Description",this.disc);
        obj.append("Image",this.image);
        return obj;
    }
    public TwitterObj(String s)
    {
        JSONObject obj = new JSONObject(s);
        this.name = obj.get("UserName").toString();
        this.link = obj.get("Link").toString();
        this.content = obj.get("Content").toString();
        this.title = obj.get("title").toString();
        this.disc = obj.get("Description").toString();
        this.image = obj.get("Image").toString();
    }
}
