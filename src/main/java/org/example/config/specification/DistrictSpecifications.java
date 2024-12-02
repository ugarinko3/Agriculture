package org.example.config.specification;

import org.example.model.entity.District;
import org.springframework.data.jpa.domain.Specification;

/**
 * Предоставляет утилитные методы для создания JPA {@link Specification}
 * для фильтрации сущностей {@link District} на основе различных критериев.
 */
public class DistrictSpecifications {

    /**
     * Создает спецификацию для фильтрации районов по названию.
     * Выполняет поиск районов, в названии которых содержится заданная подстрока, без учета регистра.
     *
     * @param name подстрока для поиска в названии района; если значение равно {@code null} или пустое, фильтр не применяется.
     * @return {@link Specification} для фильтрации районов по названию или {@code null}, если имя некорректно.
     */
    public static Specification<District> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name != null ? criteriaBuilder.equal(root.get("name"), name) : null;
    }

    /**
     * Создает спецификацию для фильтрации районов по их коду.
     *
     * @param code код района для фильтрации; если значение равно {@code null}, фильтр не применяется.
     * @return {@link Specification} для фильтрации районов по коду или {@code null}, если код некорректен.
     */
    public static Specification<District> hasCode(Integer code) {
        return (root, query, criteriaBuilder) ->
                code != null ? criteriaBuilder.equal(root.get("code"), code) : null;
    }

    /**
     * Создает спецификацию для исключения архивных районов.
     * Предполагается, что поле `status` в сущности {@link District} является логическим,
     * где значение {@code false} обозначает неархивные районы.
     *
     * @return {@link Specification} для исключения архивных районов.
     */
    public static Specification<District> isNotArchived() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("status"));
    }
}