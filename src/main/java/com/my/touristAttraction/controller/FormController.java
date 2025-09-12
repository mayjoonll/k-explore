package com.my.touristAttraction.controller;

import com.my.touristAttraction.Service.AccommodationService;
import com.my.touristAttraction.Service.KakaoAddressSearchService;
import com.my.touristAttraction.Service.KakaoCategorySearchService;
import com.my.touristAttraction.dto.DocumentDto;
import com.my.touristAttraction.dto.InputDto;
import com.my.touristAttraction.dto.KakaoApiResponseDto;
import com.my.touristAttraction.dto.OutputDto;

import com.my.touristAttraction.entity.Accommodation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class FormController {
    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final KakaoCategorySearchService kakaoCategorySearchService;
    private final AccommodationService Accommodationservice;
    public FormController(KakaoAddressSearchService kakaoAddressSearchService, KakaoCategorySearchService kakaoCategorySearchService, AccommodationService service) {
        this.kakaoAddressSearchService = kakaoAddressSearchService;
        this.kakaoCategorySearchService = kakaoCategorySearchService;
        this.Accommodationservice = service;
    }


    @GetMapping("/output")
    public String outputForm(){
        return "output";
    }

    @PostMapping("/search")
    public String searchAddress(InputDto dto, Model model){
        // 주소검색 api 호출
        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService
                .requestAddressSearch(dto.getAddress());

        // 카테고리 서비스 호출을 위해서 documents 배열에서 0번째 것 만 뺀다.
        DocumentDto documentDto = kakaoApiResponseDto
                .getDocumentlist()
                .get(0);
        // 카테고리 검색
        double radius = 1000;
        KakaoApiResponseDto recommendationDto =
                kakaoCategorySearchService.resultCategorySearch(
                        documentDto.getLatitude(),
                        documentDto.getLongitude(),
                        radius);
        // recommendationDto 에서 3개만 추출
        List<OutputDto> outputDtoList =
                kakaoCategorySearchService.makeOutputDto(
                        recommendationDto.getDocumentlist());
        System.out.println(outputDtoList);
        model.addAttribute("outputList", outputDtoList);
        return "output";
    }

    @GetMapping("/result")
    public String resultForm(){
        return "result";
    }


    @GetMapping("/kakaoMap")
    public String kakaoMapForm(){
        return "kakaoMap";
    }

    @GetMapping("/kakaoResult")
    public String kakaoResultForm(){
        return "kakaoResult";
    }

    @GetMapping("/kakaoResult2")
    public String showAccommodation(Model model) {
        List<Accommodation> accommodations = Accommodationservice.getAll();
        model.addAttribute("accommodations", accommodations);
        return "kakaoResult2"; // accommodation.html
    }

    @GetMapping("/kakaoResult3")
    public String showAccommodation(@RequestParam(defaultValue = "0") int page,
                                    Model model) {
        int size = 5; // 페이지당 숙박 개수
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Accommodation> accommodations = Accommodationservice.getAll(pageable);

        model.addAttribute("accommodations", accommodations.getContent()); // 현재 페이지 데이터
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", accommodations.getTotalPages());

        return "kakaoResult3";
    }

    @GetMapping("/kakaoResult4")
    public String showForm(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(value = "keyword", required = false) String keyword,
                           Model model) {
        int size = 5;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<Accommodation> accommodations;

        // 키워드가 있을 경우 검색, 없을 경우 전체 조회
        if (keyword != null && !keyword.trim().isEmpty()) {
            accommodations = Accommodationservice.searchByKeyword(keyword.trim(), pageable);
        } else {
            accommodations = Accommodationservice.getAll(pageable);
        }

        model.addAttribute("accommodations", accommodations.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", accommodations.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "kakaoResult4";
    }

}
