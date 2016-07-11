package mx.places.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by miguel_angel on 11/07/16.
 */
public class CommentsList implements Serializable {

    @SerializedName("noComentarios")
    private int nComments;

    @SerializedName("calificacionTotal")
    private Double sumQualification;

    @SerializedName("comentarios")
    private List<Comments> commentsList;

    public int getnComments() {
        return nComments;
    }

    public void setnComments(int nComments) {
        this.nComments = nComments;
    }

    public Double getSumQualification() {
        return sumQualification;
    }

    public void setSumQualification(Double sumQualification) {
        this.sumQualification = sumQualification;
    }

    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }
}
