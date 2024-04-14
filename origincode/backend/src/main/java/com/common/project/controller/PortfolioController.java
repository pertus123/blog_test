package com.common.project.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.common.project.dao.PortfolioDao;
import com.common.project.model.portfolio.InputPortfolio;
import com.common.project.model.portfolio.Portfolio;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Transactional
@Api(tags = "PortfolioController", description = "게시글 API")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
public class PortfolioController {
	@Autowired
	PortfolioDao portfolioDao;

	@GetMapping("/portfolio/detailPage/{email}")
	@ApiOperation(value = "상세페이지")
	public Object detailPage(@RequestParam String email) {
		Optional<Portfolio> oneFoilo = portfolioDao.findByEmail(email);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(oneFoilo.isPresent()) {
			resultMap.put("folio", oneFoilo);
			resultMap.put("sw", 1);
			return new ResponseEntity<>(resultMap, HttpStatus.OK);
		}
		else {
			resultMap.put("folio", null);
			resultMap.put("sw", 0);
			return new ResponseEntity<>(resultMap, HttpStatus.OK);
		}
	}

	@PostMapping("/portfolio/writePage")
	@ApiOperation(value = "포트폴리오 글쓰기", httpMethod = "POST", produces = "multipart/form-data")
	public Object writePage(@RequestBody InputPortfolio inputPortfolio) {

		Portfolio folio = new Portfolio(inputPortfolio.getFid(), inputPortfolio.getEmail(),
				inputPortfolio.getContent());
		portfolioDao.save(folio);

		return new ResponseEntity<>(folio, HttpStatus.OK);
	}
	
	@PostMapping("/portfolio/uploadImg")
	@ApiOperation(value = "포트폴리오 파일첨부", httpMethod = "POST", produces = "multipart/form-data")
	public Object uploadimg(MultipartFile img) {
		

		String saveName = null;
		String UPLOAD_PATH = "/var/www/html/dist/spa/postimg"; // 서버
		UUID uuid = UUID.randomUUID();
		saveName = uuid + "_" + img.getOriginalFilename();
		File saveFile = new File(UPLOAD_PATH, saveName);
		try {
			saveName = "http://i4b103.p.ssafy.io/postimg/" + saveName;
			img.transferTo(saveFile);//저장완료
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(saveName, HttpStatus.OK);
	}

	@PostMapping("/portfolio/updatePage")
	@ApiOperation(value = "글 수정")
	public Object updatePage(@RequestBody InputPortfolio inputPortfolio) {
		 portfolioDao.updateByFid(inputPortfolio.getContent(), inputPortfolio.getFid());
		 Optional<Portfolio> oneFoilo = portfolioDao.findByEmail(inputPortfolio.getEmail());
		return new ResponseEntity<>(oneFoilo, HttpStatus.OK);
	}

}
