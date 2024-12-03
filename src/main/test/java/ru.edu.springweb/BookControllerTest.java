package ru.edu.springweb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.edu.springweb.api.BookController;
import ru.edu.springweb.dao.Book;
import ru.edu.springweb.exception.BookNotFoundException;
import ru.edu.springweb.service.BookService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void testGetAllBooks() throws Exception {
        var book1 = new Book(1L, "Война и мир", "Толстой", 1000);
        var book2 = new Book(2L, "Преступление и наказание", "Достоевский", 1000);

        when(bookService.getAllBooks()).thenReturn(new ArrayList<>(List.of(book1, book2)));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetBookById() throws Exception {
        var book = new Book(1L, "Война и мир", "Толстой", 1000);

        when(bookService.getBookById(1L)).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Война и мир"))
                .andExpect(jsonPath("$.authorName").value("Толстой"))
                .andExpect(jsonPath("$.numberOfPages").value(1000));

        verify(bookService).getBookById(1L);
    }

    @Test
    void testAddBook() throws Exception {
        var book = new Book(1L, "Война и мир", "Толстой", 1000);

        when(bookService.addBook(any())).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Война и мир\", \"authorName\": \"Толстой\", \"numberOfPages\": 1000}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Война и мир"));

        verify(bookService).addBook(any());
    }

    @Test
    void testUpdateBook() throws Exception {
        var book = new Book(1L, "Война и мир", "Толстой", 1000);

        when(bookService.updateBook(anyLong(), eq(book))).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Преступление и наказание\", \"authorName\": \"Достоевский\", \"numberOfPages\": 1000}"))
                .andExpect(status().isOk());

        verify(bookService).updateBook(eq(1L), any());
    }

    @Test
    void testDeleteBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/books/1"))
                .andExpect(status().isNoContent());

        verify(bookService).deleteBook(1L);
    }

    @Test
    void testGetBookByIdThrowsException() throws Exception {
        doThrow(new BookNotFoundException("Книга не найдена! ID: 1")).when(bookService).getBookById(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/1"))
                .andExpect(status().isNotFound());

        verify(bookService).getBookById(1L);
    }
}