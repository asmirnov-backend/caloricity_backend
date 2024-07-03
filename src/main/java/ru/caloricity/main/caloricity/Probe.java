package ru.caloricity.main.caloricity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Comment("Ингредиенты")
@Table(name = "ingredients")
public class Probe {
}
