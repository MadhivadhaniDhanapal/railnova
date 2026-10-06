package Railnova.service;

import Railnova.entity.Train;
import Railnova.repository.TrainRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainService {

    private final TrainRepository trainRepository;

    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    public Train saveTrain(Train train) {
        return trainRepository.save(train);
    }
}