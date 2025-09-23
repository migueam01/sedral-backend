package com.uce.sedral.services.impl;

import com.uce.sedral.models.entities.Responsable;
import com.uce.sedral.repositories.IResponsableRepo;
import com.uce.sedral.services.IResponsableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResponsableServiceImpl implements IResponsableService {

    private final IResponsableRepo repo;

    @Override
    public Responsable save(Responsable responsable) {
        return repo.save(responsable);
    }

    @Override
    public Responsable update(Responsable responsable) {
        return repo.save(responsable);
    }

    @Override
    public Responsable findById(Integer id) {
        Optional<Responsable> responsable = repo.findById(id);
        return responsable.orElseGet(Responsable::new);
    }

    @Override
    public List<Responsable> findAll() {
        return repo.findAll();
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
