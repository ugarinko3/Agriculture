package org.example.config.specification;

import org.example.model.entity.Farmer;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Предоставляет утилитные методы для создания JPA {@link Specification}
 * для фильтрации сущностей {@link Farmer} на основе различных критериев.
 */
public class FarmerSpecification {

    /**
     * Создает спецификацию для фильтрации фермеров по имени.
     * Выполняет поиск фермеров, в имени которых содержится заданная подстрока, без учета регистра.
     *
     * @param name подстрока для поиска в имени фермера; если значение равно {@code null} или пустое, фильтр не применяется.
     * @return {@link Specification} для фильтрации фермеров по имени или {@code null}, если имя некорректно.
     */
    public static Specification<Farmer> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name != null && !name.isEmpty() ?
                        criteriaBuilder.equal(root.get("name"), name)
                        : null;
    }

    /**
     * Создает спецификацию для фильтрации фермеров по ИНН.
     *
     * @param inn ИНН фермера для фильтрации; если значение равно {@code null}, фильтр не применяется.
     * @return {@link Specification} для фильтрации фермеров по ИНН или {@code null}, если ИНН некорректен.
     */
    public static Specification<Farmer> hasInn(Long inn) {
        return (root, query, criteriaBuilder) ->
                inn != null ? criteriaBuilder.equal(root.get("inn"), inn) : null;
    }

    /**
     * Создает спецификацию для фильтрации фермеров по району.
     *
     * @param districtId ID района для фильтрации; если значение равно {@code null}, фильтр не применяется.
     * @return {@link Specification} для фильтрации фермеров по району или {@code null}, если район некорректен.
     */
    public static Specification<Farmer> hasDistrict(UUID districtId) {
        return (root, query, criteriaBuilder) ->
                districtId != null ? criteriaBuilder.equal(root.get("registrationDistrict").get("id"), districtId) : null;
    }

    /**
     * Создает спецификацию для фильтрации фермеров по дате регистрации.
     *
     * @param date дата регистрации для фильтрации; если значение равно {@code null}, фильтр не применяется.
     * @return {@link Specification} для фильтрации фермеров по дате регистрации или {@code null}, если дата некорректна.
     */
    public static Specification<Farmer> hasDate(LocalDate date) {
        return (root, query, criteriaBuilder) ->
                date != null ? criteriaBuilder.equal(root.get("registrationDate"), date) : null;
    }

    /**
     * Создает спецификацию для исключения архивных фермеров.
     * Предполагается, что поле `status` в сущности {@link Farmer} является логическим,
     * где значение {@code false} обозначает неархивных фермеров.
     *
     * @return {@link Specification} для исключения архивных фермеров.
     */
    public static Specification<Farmer> isNotArchived(boolean archived) {
        return (root, query, criteriaBuilder) ->
               !archived ? criteriaBuilder.isFalse(root.get("archiveStatus")) : criteriaBuilder.isTrue(root.get("archiveStatus"));
    }
}