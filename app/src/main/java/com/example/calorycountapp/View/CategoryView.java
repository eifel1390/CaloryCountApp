package com.example.calorycountapp.View;


import com.example.calorycountapp.Model.Entity;

import java.util.List;

public interface CategoryView extends MvpView {
    void showData(List<Entity> list);
}
