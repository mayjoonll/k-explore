package com.my.touristAttraction.Service;

import com.my.touristAttraction.entity.Accommodation;
import com.my.touristAttraction.entity.Leports;
import com.my.touristAttraction.entity.Shopping;
import com.my.touristAttraction.repository.LeportsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeportsService {

    private final LeportsRepository repository;
    private final LeportsRepository leportsRepository;

    public List<Leports> getAllLeports() {
        return repository.findAll();
    }

    public Leports saveLeports(Leports spot) {
        return repository.save(spot);
    }

    public void deleteLeports(Long id) {
        repository.deleteById(id);
    }

    public Leports getLeportsById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("숙소를 찾을 수 없습니다. id=" + id));
    }

    public List<Leports> getNearby(Double lat, Double lng, Double radiusKm) {
        return leportsRepository.findAll().stream()
                .filter(s -> {
                    if (s.getMapy() == null || s.getMapx() == null) return false;
                    double distance = haversine(lat, lng, s.getMapy(), s.getMapx());
                    return distance <= radiusKm;
                })
                .toList();
    }

    // 하버사인 공식으로 거리 계산 (km 단위)
    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // 지구 반지름 (km)
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    public List<Leports> getAll() {
        return leportsRepository.findAll();
    }


    public List<Leports> findAll() {
        return leportsRepository.findAll();
    }
}