import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Xml_Handler {
    static Scanner input = new Scanner(System.in);
    static ArrayList<Book> books = new ArrayList<>();
    static String path = System.getProperty("user.dir")+"\\Books.xml";

    public static void buildDocument() {
        File inputFile = new File("Books.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        TransformerFactory transformerFactory=TransformerFactory.newInstance();
        boolean notExist = false;
        try {
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document doc;
            Element root;
            if(inputFile.exists() && !inputFile.isDirectory())
            {
                doc = builder.parse(inputFile);
                root = doc.getDocumentElement();
            }
            else
            {
                doc = builder.newDocument();
                root = doc.createElement("Catalogue");
                notExist = true;
            }

            for (Book book : books) {

                Element Book = doc.createElement("Book");
                Attr attr = doc.createAttribute("ID");
                attr.setValue(book.get_id());
                Book.setAttributeNode(attr);
                Element author = doc.createElement("Author");

                author.appendChild(doc.createTextNode(book.get_Author()));

                Element title = doc.createElement("Title");
                title.appendChild(doc.createTextNode(book.get_Title()));

                Element genre = doc.createElement("Genre");
                genre.appendChild(doc.createTextNode(book.get_Genre()));

                Element price = doc.createElement("Price");
                price.appendChild(doc.createTextNode(book.get_Price()));

                Element Date = doc.createElement("Publish_Date");
                Date.appendChild(doc.createTextNode(book.get_Publish_Date()));

                Element description = doc.createElement("Description");
                description.appendChild(doc.createTextNode(book.get_Description()));

                Book.appendChild(author);
                Book.appendChild(title);
                Book.appendChild(genre);
                Book.appendChild(price);
                Book.appendChild(Date);
                Book.appendChild(description);
                root.appendChild(Book);
            }
            if(notExist)
                doc.appendChild(root);

            // write from memory to file
            DOMSource source = new DOMSource(doc);
            // create result stream
            Result result = new StreamResult(inputFile);

            Transformer transformer=transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(source, result);
            System.out.println("write data success to file "+path);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteBook(String id) throws ParserConfigurationException, TransformerException{
        try {
            File file = new File("Books.xml");
            DocumentBuilderFactory DBFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder DBuilder = DBFactory.newDocumentBuilder();
            Document document = DBuilder.parse(file);
            NodeList nodes = document.getElementsByTagName("Book");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element book= (Element)nodes.item(i);
                if(book.getAttribute("ID" ).equals(id)){
                    book.getParentNode().removeChild(book);
                }

            }

            TransformerFactory TFactory = TransformerFactory.newInstance();
            Transformer Tformer = TFactory.newTransformer();
            DOMSource src = new DOMSource(document);
            StreamResult consoleResult = new StreamResult("Books.xml");
            Tformer.transform(src, consoleResult);
        } catch (SAXException | IOException ex) {
            Logger.getLogger(Xml_Handler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static void searchBook(String input) throws ParserConfigurationException{
        try {
            File file = new File("Books.xml");
            DocumentBuilderFactory DBFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder DBuilder = DBFactory.newDocumentBuilder();
            Document document = DBuilder.parse(file);
            NodeList nodes = document.getElementsByTagName("Book");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element= (Element)node;
                    String author = element.getElementsByTagName("Author").item(0).getTextContent();
                    String title = element.getElementsByTagName("Title").item(0).getTextContent();
                    if(author.contains(input) || title.contains(input)){
                        System.out.println("Book ID: " + element.getAttribute("ID"));
                        System.out.println("author: " + element.getElementsByTagName("Author").item(0).getTextContent());
                        System.out.println("title: " + element.getElementsByTagName("Title").item(0).getTextContent());
                        System.out.println("genre: " + element.getElementsByTagName("Genre").item(0).getTextContent());
                        System.out.println("price: " + element.getElementsByTagName("Price").item(0).getTextContent());
                        System.out.println("Publish_Date: " + element.getElementsByTagName("Publish_Date").item(0).getTextContent());
                        System.out.println("Description: " + element.getElementsByTagName("Description").item(0).getTextContent());
                    }

                }

            }
        } catch (SAXException | IOException ex) {
            Logger.getLogger(Xml_Handler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws TransformerException {
        while (true)
        {
            System.out.println("Select your option");
            System.out.println("1- Add");
            System.out.println("2- Delete");
            System.out.println("3- Search");
            System.out.println("4- Exit");
            int selection = input.nextInt();

             if (selection == 1) {
                 System.out.println("Enter the Number of Books");
                 int Books_num = input.nextInt();
                 Book book;
                 for (int i = 0; i < Books_num; i++) {
                     book = new Book();
                     System.out.println("Enter Book No." + (i + 1) + "\n======================");

                     System.out.println("Enter Book ID");
                     book.set_id(input.next());

                     System.out.println("Enter Book Author");
                     book.set_Author(input.next());

                     System.out.println("Enter Book Title");
                     book.set_Title(input.next());

                     System.out.println("Enter Book Genre");
                     book.set_Genre(input.next());

                     System.out.println("Enter Book Price");
                     book.set_Price(input.next());

                     System.out.println("Enter Book Publish_Date");
                     book.set_Publish_Date(input.next());

                     System.out.println("Enter Book Description");
                     input.nextLine();
                     book.set_Description(input.nextLine());

                     books.add(book);
                 }
                 buildDocument();
             } else if (selection == 2) {
                 System.out.println("Enter the ID OF the Book");
                 input.nextLine();
                 String delID = input.nextLine();
                 try {
                     deleteBook(delID);

                 } catch (ParserConfigurationException ex) {
                     Logger.getLogger(Xml_Handler.class.getName()).log(Level.SEVERE, null, ex);
                 }
             } else if (selection == 3) {
                 System.out.println("Enter The Name of Author Or Title");
                 input.nextLine();
                 String AuthorORTitle = input.nextLine();
                 try {
                     searchBook(AuthorORTitle);
                 } catch (ParserConfigurationException ex) {
                     Logger.getLogger(Xml_Handler.class.getName()).log(Level.SEVERE, null, ex);
                 }
             } else
                 break;
        }
    }
}