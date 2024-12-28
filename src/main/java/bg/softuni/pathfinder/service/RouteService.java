package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.data.RouteRepository;
import bg.softuni.pathfinder.model.Picture;
import bg.softuni.pathfinder.model.Route;
import bg.softuni.pathfinder.service.dto.RouteShortInfoDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class RouteService  {

    private final RouteRepository routeRepository;
    private Random random;
    private ModelMapper modelMapper;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
        this.random = new Random();
        this.modelMapper = new ModelMapper();
    }

    @Transactional
    public List<RouteShortInfoDto> getAll() {
       return routeRepository.findAll()
                .stream()
                .map(this::mapTpShortInfo)
                .toList();
    }



    @Transactional
    public RouteShortInfoDto   getRandomRoute() {
        long routeCount = routeRepository.count();
        long randomId = random.nextLong(routeCount) + 1;

        Optional<Route> route = this.routeRepository.findById(randomId);

        if (route.isEmpty()) {

        }
        RouteShortInfoDto dto = modelMapper.map(route.get(), RouteShortInfoDto.class);
        Optional<Picture> first = route.get().getPictures().stream().findFirst();
        dto.setImageUrl(first.get().getUrl());
        return dto;
    }


    private RouteShortInfoDto mapTpShortInfo(Route route) {
        RouteShortInfoDto dto = modelMapper.map(route, RouteShortInfoDto.class);

        Optional<Picture> first = route.getPictures().stream().findFirst();
        dto.setImageUrl(first.get().getUrl());

        return dto;
    }
}
