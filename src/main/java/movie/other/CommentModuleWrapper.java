package movie.other;

import java.util.List;

import movie.bean.CommentDTO;

public class CommentModuleWrapper {
	String order;
	CommentPaging paging;
	List<CommentDTO> commentList;
	CommentDTO myComment;
	
	public String getOrder() {
		return order;
	}
	
	public CommentPaging getPaging() {
		return paging;
	}
	
	public List<CommentDTO> getCommentList() {
		return commentList;
	}
	
	public CommentDTO getMyComment() {
		return myComment;
	}
	
	public void setOrder(String order) {
		this.order = order;
	}
	
	public void setPaging(CommentPaging paging) {
		this.paging = paging;
	}
	
	public void setCommentList(List<CommentDTO> commentList) {
		this.commentList = commentList;
	}
	
	public void setMyComment(CommentDTO myComment) {
		this.myComment = myComment;
	}
}