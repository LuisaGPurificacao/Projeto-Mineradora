package org.br.mineradora.scheduler;

import io.quarkus.scheduler.Scheduled;
import org.br.mineradora.service.QuotationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class QuotationScheduler {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Inject
    QuotationService quotationService;

    @Transactional
    @Scheduled(every = "35s", identity = "task-job")
    void schedule() {
        LOG.info("\n-- Executando scheduler --");
        quotationService.getCurrencyPrice();
    }

}
