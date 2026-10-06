package Railnova.controller;

import Railnova.entity.Train;
import Railnova.service.TrainService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trains")
@CrossOrigin
public class TrainController {

    private final TrainService trainService;

    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @GetMapping
    public List<Train> getAllTrains() {
        return trainService.getAllTrains();
    }

    @PostMapping
    public Train createTrain(@RequestBody Train train) {
        return trainService.saveTrain(train);
    }
}