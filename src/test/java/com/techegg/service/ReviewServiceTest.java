package com.techegg.service;

import com.techegg.domain.Review;
import com.techegg.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Review r = new Review();
        r.setId(1L);
        when(reviewRepository.findAll()).thenReturn(List.of(r));
        List<Review> result = reviewService.findAll();
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void testFindById() {
        Review r = new Review();
        r.setId(2L);
        when(reviewRepository.findById(2L)).thenReturn(Optional.of(r));
        Optional<Review> opt = reviewService.findById(2L);
        assertTrue(opt.isPresent());
        assertEquals(2L, opt.get().getId());
    }

    @Test
    void testSave() {
        Review r = new Review();
        r.setComment("Nice");
        when(reviewRepository.save(r)).thenReturn(r);
        Review saved = reviewService.save(r);
        assertNotNull(saved);
        assertEquals("Nice", saved.getComment());
    }

    @Test
    void testDeleteById() {
        doNothing().when(reviewRepository).deleteById(5L);
        reviewService.deleteById(5L);
        verify(reviewRepository, times(1)).deleteById(5L);
    }

    @Test
    void testFindByProductId() {
        Review r = new Review();
        r.setId(3L);
        when(reviewRepository.findByProductId(10L)).thenReturn(List.of(r));
        List<Review> list = reviewService.findByProductId(10L);
        assertEquals(1, list.size());
        assertEquals(3L, list.get(0).getId());
    }
}
