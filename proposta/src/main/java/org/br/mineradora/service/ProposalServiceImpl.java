package org.br.mineradora.service;

import org.br.mineradora.dto.ProposalDTO;
import org.br.mineradora.dto.ProposalDetailsDTO;
import org.br.mineradora.entity.ProposalEntity;
import org.br.mineradora.message.KafkaEvent;
import org.br.mineradora.repository.ProposalRepository;

import javax.inject.Inject;
import java.util.Date;

public class ProposalServiceImpl implements ProposalService {

    @Inject
    ProposalRepository repository;

    @Inject
    KafkaEvent kafkaMessages;

    @Override
    public ProposalDetailsDTO findFullProposal(Long id) {
        ProposalEntity proposal = repository.findById(id);
        return ProposalDetailsDTO.builder()
                .proposalId(proposal.getId())
                .proposalValidityDays(proposal.getProposalValidityDays())
                .country(proposal.getCountry())
                .priceTonne(proposal.getPriceTonne())
                .customer(proposal.getCustomer())
                .tonnes(proposal.getTonnes())
                .build();
    }

    @Override
    public void createNewProposal(ProposalDetailsDTO proposalDetailsDTO) {

        ProposalDTO proposal = buildAndSaveNewProposal(proposalDetailsDTO);
        kafkaMessages.sendNewKafkaEvent(proposal);

    }

    @Override
    public void removeProposal(Long id) {

    }

    private ProposalDTO buildAndSaveNewProposal(ProposalDetailsDTO proposalDetailsDTO) {
        try {
            ProposalEntity proposal = new ProposalEntity();
            proposal.setCreated(new Date());
            proposal.setProposalValidityDays(proposalDetailsDTO.getProposalValidityDays());
            proposal.setCountry(proposalDetailsDTO.getCountry());
            proposal.setCustomer(proposalDetailsDTO.getCustomer());
        }
    }

}
