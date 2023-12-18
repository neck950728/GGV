package movie.other;

public class CommentPaging {
	private int startNum;
	private int endNum;
	private int totalPage;
	private int currentPage;
	private int startPage;
	private int endPage;
	
	private final int PERPAGE_COMMENT_COUNT = 10;
	private final int PAGE_NAV_COUNT = 10;
	
	public CommentPaging(int page, int total_commentCount) {
		currentPage = page;
		endNum = page * PERPAGE_COMMENT_COUNT;
		startNum = endNum - (PERPAGE_COMMENT_COUNT - 1);
		
		totalPage = (total_commentCount + (PERPAGE_COMMENT_COUNT - 1)) / PERPAGE_COMMENT_COUNT;
		startPage = (page - 1) / PAGE_NAV_COUNT * PAGE_NAV_COUNT + 1;
		endPage = startPage + PAGE_NAV_COUNT - 1;
		if(totalPage < endPage) endPage = totalPage;
	}

	public int getStartNum() {
		return startNum;
	}

	public int getEndNum() {
		return endNum;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public int getPAGE_NAV_COUNT() {
		return PAGE_NAV_COUNT;
	}
}