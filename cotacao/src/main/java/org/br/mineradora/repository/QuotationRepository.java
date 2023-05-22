package org.br.mineradora.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.br.mineradora.entity.QuotationEntity;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface QuotationRepository extends PanacheRepository<QuotationEntity> {
}
