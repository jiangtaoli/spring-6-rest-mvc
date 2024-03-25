package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;

    @PatchMapping(value = BEER_PATH_ID)
    public ResponseEntity updateBeerPatchById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beerDTO) {

        beerService.patchById(beerId, beerDTO);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = BEER_PATH_ID)
    public ResponseEntity deteleById(@PathVariable("beerId") UUID beerId) {

        beerService.deleteById(beerId);

        return new ResponseEntity((HttpStatus.NO_CONTENT));
    }
    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateBeerById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beerDTO) {


        beerService.updateBeerById(beerId, beerDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PostMapping(value = BEER_PATH)
//    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity handlePost(@RequestBody BeerDTO beerDTO) {
        BeerDTO savedBeerDTO = beerService.saveNewBeer(beerDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeerDTO.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = BEER_PATH)
    public List<BeerDTO> listBeers() {
        return beerService.listBeers();
    }

    @GetMapping(value = BEER_PATH_ID)
    public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId) {

        log.debug("Get Beer by id - in controller - 1234");

        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }

}
