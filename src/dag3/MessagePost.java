package dag3;

import java.util.ArrayList;


/**
 * This class stores information about a post in a social network news feed. 
 * The main part of the post consists of a (possibly multi-line)
 * text message. Other data, such as author and time, are also stored.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 0.2
 */
public class MessagePost extends Post
{
    private String message;  // an arbitrarily long, multi-line message
    private String author;
    
    /**
     * Constructor for objects of class MessagePost.
     * 
     * @param author    The username of the author of this post.
     * @param text      The text of this post.
     */
    public MessagePost(String author, String text)
    {
        super(author);
        this.author = author;
        message = text;
    }

    /**
     * Return the text of this post.
     * 
     * @return The post's message text.
     */
    public String getText()
    {
        return message;
    }
    
    
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        } else if(!(obj instanceof MessagePost)){
            return false;
        }
        MessagePost other = (MessagePost) obj;
        return author.equals(other.author) && message.equals(other.message);
    }
    
    
}
