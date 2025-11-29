package com.uce.sedral.services.impl;

import com.uce.sedral.models.entities.Menu;
import com.uce.sedral.repositories.IMenuRepo;
import com.uce.sedral.services.IMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements IMenuService {

    private final IMenuRepo repo;

    @Override
    public List<Menu> listarMenuPorUsuario(String nombre) {
        List<Menu> menus = new ArrayList<>();
        repo.listarMenuPorUsuario(nombre).forEach(x -> {
            Menu m = new Menu();
            m.setIdMenu(Integer.parseInt(String.valueOf(x[0])));
            m.setIcono(String.valueOf(x[1]));
            m.setNombre(String.valueOf(x[2]));
            m.setUrl(String.valueOf(x[3]));
            menus.add(m);
        });
        return menus;
    }

    @Override
    public Menu save(Menu menu) {
        return repo.save(menu);
    }

    @Override
    public Menu update(Menu menu) {
        return repo.save(menu);
    }

    @Override
    public Menu findById(Integer id) {
        Optional<Menu> menu = repo.findById(id);
        return menu.orElseGet(Menu::new);
    }

    @Override
    public List<Menu> findAll() {
        return repo.findAll();
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }
}