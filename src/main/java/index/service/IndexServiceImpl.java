package index.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import admin.movie.bean.MovieDTO;
import movie.dao.MovieDAO;

@Service
public class IndexServiceImpl implements IndexService {
	@Autowired
	private MovieDAO movieDAO;
	
	@Override
	public MovieDTO getRandomMovie() {
		return movieDAO.selectRandomMovie();
	}
}