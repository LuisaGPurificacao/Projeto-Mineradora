package org.br.mineradora.service;

import org.br.mineradora.dto.OpportunityDTO;
import org.br.mineradora.dto.ProposalDTO;
import org.br.mineradora.dto.QuotationDTO;
import org.br.mineradora.entity.OpportunityEntity;
import org.br.mineradora.entity.QuotationEntity;
import org.br.mineradora.repository.OpportunityRepository;
import org.br.mineradora.repository.QuotationRepository;
import org.br.mineradora.utils.CSVHelper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class OpportunityServiceImpl implements OpportunityService {

    @Inject
    QuotationRepository quotationRepository;

    @Inject
    OpportunityRepository opportunityRepository;

    @Override
    public void buildOpportunity(ProposalDTO proposal) {
        List<QuotationEntity> quotationEntities = quotationRepository.findAll().list();
        Collections.reverse(quotationEntities);

        OpportunityEntity opportunity = new OpportunityEntity();
        opportunity.setDate(new Date());
        opportunity.setProposalId(proposal.getProposalId());
        opportunity.setCustomer(proposal.getCustomer());
        opportunity.setPriceTonne(proposal.getPriceTonne());
        opportunity.setLastDollarQuotation(quotationEntities.get(0).getCurrencyPrice());

        opportunityRepository.persist(opportunity);
    }

    @Override
    public void saveQuotation(QuotationDTO quotation) {

    }

    @Override
    public List<OpportunityDTO> generateOpportunityData() {
        return null;
    }

    @Override
    public ByteArrayInputStream generateCSVOpportunityReport() {
        List<OpportunityDTO> opportunityList = new ArrayList<>();

        opportunityRepository.findAll().list().forEach(item -> {
            opportunityList.add(OpportunityDTO.builder()
                    .proposalId(item.getProposalId())
                    .customer(item.getCustomer())
                    .priceTonne(item.getPriceTonne())
                    .lastDollarQuotation(item.getLastDollarQuotation())
                    .build()
            );
        });

        return CSVHelper.opportunitiesToCSV(opportunityList);
    }

}
