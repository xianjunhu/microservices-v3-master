	package microservices.book.multiplication.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.service.MultiplicationService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class provides a REST API to POST the attempts from users.
 */
@RestController
@RequestMapping("/results")
final class MultiplicationResultAttemptController {

    private final MultiplicationService multiplicationService;

    @Autowired
    MultiplicationResultAttemptController(final MultiplicationService multiplicationService) {
        this.multiplicationService = multiplicationService;
    }

/*    @RequiredArgsConstructor
    @NoArgsConstructor(force = true)
    @Getter
*/
    public final class ResultResponse {
		public boolean correct;	
		public ResultResponse(boolean b) {
			this.correct = b;
		}
    }
    
    @PostMapping
    ResponseEntity<ResultResponse> postResult(@RequestBody
    		MultiplicationResultAttempt multiplicationResultAttempt) {
    	    
    	    ResultResponse resp = new ResultResponse(multiplicationService
        		.checkAttempt(multiplicationResultAttempt));
    	
    	    return new ResponseEntity<ResultResponse>(resp,HttpStatus.OK);
    	}
 
    /*
    @PostMapping
    ResponseEntity<MultiplicationResultAttempt> postResult(@RequestBody 
    		                            MultiplicationResultAttempt multiplicationResultAttempt) {
    	    
    		boolean isCorrect = multiplicationService.checkAttempt(multiplicationResultAttempt);
            MultiplicationResultAttempt attemptCopy = new MultiplicationResultAttempt(
                    multiplicationResultAttempt.getUser(),
                    multiplicationResultAttempt.getMultiplication(),
                    multiplicationResultAttempt.getResultAttempt(),
                    isCorrect
            );
//            return ResponseEntity.ok(attemptCopy);
    	    return new ResponseEntity<MultiplicationResultAttempt>(attemptCopy,HttpStatus.OK);
    	}*/
    
    @GetMapping
    ResponseEntity<List<MultiplicationResultAttempt>> getStatistics(@RequestParam("alias") String alias) 
    {
    	return ResponseEntity.ok(multiplicationService.getStatsForUser(alias));
    }
    

    @GetMapping("/{resultId}")
    ResponseEntity<MultiplicationResultAttempt> getResultById(final @PathVariable("resultId") Long resultId) {
        return ResponseEntity.ok(
                multiplicationService.getResultById(resultId));
    }

}