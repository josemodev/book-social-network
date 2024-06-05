package com.morillo.book.book;

import com.morillo.book.common.PageResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Tag(name = "Book")
public class BookController {

    private final BookService service;

    @PostMapping
    public ResponseEntity<Integer> saveBook(
            @Valid @RequestBody BookRequest request,
            Authentication connectedUser                                                        // Authentication SIRVE PARA
    ) {
        return ResponseEntity.ok(service.save(request, connectedUser));
    }

    @GetMapping("/{book-id}")
    public ResponseEntity<BookResponse> findBookById(                                           // SE OBTIENE SOLO UN LIBRO. PUEDE SER USADO POR LA LISTA OBTENIDA DE LOS USUARIOS LOGEADO Y LOS DEMAS USUARIO.
            @PathVariable("book-id") Integer bookId
    ) {
        return ResponseEntity.ok(service.findById(bookId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(                             // LISTA TODOS LOS LIBROS MENOS EL DEL USUARIO LOGEADO. IMPLEMENTA LA PAGINACION DE HIBERNATE Y SPRING JPA. USADO PARA NO DEVOLVER TODO LOS DATOS SINO LA CANTIDAD QUE SE CODIFIQUE
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllBooks(page, size, connectedUser));
    }

    @GetMapping("/owner")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwner(                      // LISTA TODOS LOS LIBROS SOLO DEL USUARIO LOGEADO. IMPLEMENTA LA PAGINACION DE HIBERNATE Y SPRING JPA. USADO PARA NO DEVOLVER TODO LOS DATOS SINO LA CANTIDAD QUE SE CODIFIQUE
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllBooksByOwner(page, size, connectedUser));
    }

    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooks(             // LISTA TODOS LOS LIBROS QUE EL USUARIO LOGEADO HA QUITADO PRESTADO A LOS DEMAS USUARIO. EL USUARIO LOGEADO NO PUEDE QUITAR PRESTADO SUS PROPIOS LIBROS
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllBorrowedBooks(page, size, connectedUser));
    }

    @GetMapping("/returned")                                                                    
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooks(             // QUERY QUE RETORNA TODOS LOS LIBROS QUE LE FUERON DEVUELTOS HACIA EL USUARIO AUTENTICADO PARA FINALMENTE CONFIRMAR LA DEVOLUCION
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllReturnedBooks(page, size, connectedUser));
    }

    @PatchMapping("/shareable/{book-id}")                                                      
    public ResponseEntity<Integer> updateShareableStatus(                                       // SHARABLE. METODO HTTP PARA ACTUALIZAR. SOLO EL DUENO DEL LIBRO PODRA ACTUALIZAR SUS PROPIOS LIBROS
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.updateShareableStatus(bookId, connectedUser));
    }

    @PatchMapping("/archived/{book-id}")        
    public ResponseEntity<Integer> updateArchivedStatus(                                        // ARCHIVED. METODO HTTP PARA ACTUALIZAR. SOLO EL DUENO DEL LIBRO PODRA ACTUALIZAR SUS PROPIOS LIBROS
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.updateArchivedStatus(bookId, connectedUser));
    }

    @PostMapping("borrow/{book-id}")
    public ResponseEntity<Integer> borrowBook(                                                  // TOMAR PRESTADO LOS LIBROS MENOS EL DEL USUARIO AUTENTICADO.
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.borrowBook(bookId, connectedUser));
    }

    @PatchMapping("borrow/return/{book-id}")                                                    
    public ResponseEntity<Integer> returnBorrowBook(                                            // DEVOLVER EL LIBRO QUE LE FUE TOMADO A CUALQUIER USUARIO, EXCLUYENDO LOS LIBRO DEL USUARIO LOGEADO, YA QUE, NO LE SERA PERMITIDO TOMAR LOS SUYOS.
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.returnBorrowedBook(bookId, connectedUser));
    }

    @PatchMapping("borrow/return/approve/{book-id}")                                            
    public ResponseEntity<Integer> approveReturnBorrowBook(                                     // CONFIRMAR POR EL DUENO DE LOS LIBROS AQUELLOS QUE HAN SIDO DEVUELTOS
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.approveReturnBorrowedBook(bookId, connectedUser));
    }

    @PostMapping(value = "/cover/{book-id}", consumes = "multipart/form-data")                  // consumes ES USADO PARA CARGAR UN FILE.
    public ResponseEntity<?> uploadBookCoverPicture(                                            // EL ? ES PORQUE AUN NO SE SABE QUE VA A RETORNAR.
            @PathVariable("book-id") Integer bookId,
            @Parameter()                                                                        // @Parameter       ?
            @RequestPart("file") MultipartFile file,                                            // @RequestPart     ?
            Authentication connectedUser
    ) {
        service.uploadBookCoverPicture(file, connectedUser, bookId);
        return ResponseEntity.accepted().build();
    }
}
