package movie.other;

public class TrailerPaging {
	private int startNum;
	private int endNum;
	private int totalPage;
	private int currentPage;
	private int startPage;
	private int endPage;
	
	private final int PERPAGE_TRAILER_COUNT = 16;
	private final int PAGE_NAV_COUNT = 10;
	
	public TrailerPaging(int page, int total_trailerCount) {
		currentPage = page;
		endNum = page * PERPAGE_TRAILER_COUNT;
		startNum = endNum - (PERPAGE_TRAILER_COUNT - 1);
		
		totalPage = (total_trailerCount + (PERPAGE_TRAILER_COUNT - 1)) / PERPAGE_TRAILER_COUNT;
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