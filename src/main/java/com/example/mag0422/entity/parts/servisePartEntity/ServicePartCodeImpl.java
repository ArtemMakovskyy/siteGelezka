package com.example.mag0422.entity.parts.servisePartEntity;

import com.example.mag0422.dao.dao_parts.repository.PartCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServicePartCodeImpl implements PartCodeService{
    @Autowired
    PartCodeRepository partCodeRepository;
    private PartCode partCode;

    @Override
    public PartCode checkData(PartCode partCode) {

        System.out.println("checkData");
        System.out.println(partCode.getBrinePartNumber() + " " + "prn");
        if (partCode.getGelezkaPartNumber() == "") {
            partCode.setGelezkaPartNumber(null);
        }

        if (partCode.getBrinePartNumber() == "") {
            partCode.setBrinePartNumber(null);
        }
        if (partCode.getOriginalPartNumber() == "") {
            partCode.setOriginalPartNumber(null);
        }
        return partCode;
    }
    @Override
    public Long serchPositionOfCCode(PartCode inerCode) {
        System.out.println("serchPositionOfCCode(PartCode sqlCode,PartCode inerCode)");
        String brine = inerCode.getBrinePartNumber();
        String gelezka = inerCode.getGelezkaPartNumber();
        String original = inerCode.getOriginalPartNumber();

        List<PartCode> codeList = partCodeRepository.findAll();

        Long lastPosition = Long.valueOf((codeList.get(codeList.size() - 1)).getId());
//serch for last element
        if (
                (partCodeRepository.findById(lastPosition).get().getBrinePartNumber() == (brine) ||
                        partCodeRepository.findById(lastPosition).get().getBrinePartNumber().equals(brine))
                        &&
                        (partCodeRepository.findById(lastPosition).get().getGelezkaPartNumber() == (gelezka) ||
                                partCodeRepository.findById(lastPosition).get().getGelezkaPartNumber().equals(gelezka))
                        &&
                        (partCodeRepository.findById(lastPosition).get().getOriginalPartNumber() == (original) ||
                                partCodeRepository.findById(lastPosition).get().getOriginalPartNumber().equals(original))
        ) {
            System.out.println("Обработано одно соответствие in erchPositionOfCCode(PartCode inerCode)");
            return lastPosition;
        }
//serch for all elements
        for (int position = 0; position < codeList.size(); position++) {
            if (
                    (codeList.get(position).getBrinePartNumber() == (brine) ||
                            codeList.get(position).getBrinePartNumber().equals(brine))
                            &&
                            (codeList.get(position).getGelezkaPartNumber() == (gelezka) ||
                                    codeList.get(position).getGelezkaPartNumber().equals(gelezka))
                            &&
                            (codeList.get(position).getOriginalPartNumber() == (original) ||
                                    codeList.get(position).getOriginalPartNumber().equals(original))) {
                System.err.println("Обработаны все соответствия " + codeList.get(position).getId());
                return Long.valueOf(codeList.get(position).getId());
            }
        }
        //if doesn't find
        System.err.println("Нет позиции");
        return null;
    }
    @Override
    public List serchCodesForEquals(PartCode partCode) {
        List<PartCode> codesList = partCodeRepository.findAll();
        List<Object>codeDates_0bollean_1string = new ArrayList<>();
        if (partCode.getBrinePartNumber() != null) {
            System.out.println("partCode.getBrinePartNumber()!=null");
            for (int i = 0; i < codesList.size(); i++) {
                if (codesList.get(i).getBrinePartNumber() == null) {
                    continue;
                } else if (codesList.get(i).getBrinePartNumber().equals(partCode.getBrinePartNumber())) {
                    codeDates_0bollean_1string.add(true);
                    codeDates_0bollean_1string.add("brain code: " + partCode.getBrinePartNumber() + " exists in database");
                    return codeDates_0bollean_1string;
                }
            }
        }
        //serch original
        if (partCode.getOriginalPartNumber() != null) {
            System.out.println("partCode.getOriginalPartNumber()!=null");
            for (int i = 0; i < codesList.size(); i++) {
                if (codesList.get(i).getOriginalPartNumber() == null) {
                    continue;
                } else if (codesList.get(i).getOriginalPartNumber().equals(partCode.getOriginalPartNumber())) {
                    codeDates_0bollean_1string.add(true);
                    codeDates_0bollean_1string.add("original code: " + partCode.getOriginalPartNumber() + " exists in database");
                    return codeDates_0bollean_1string;
                }
            }
        }
        //serch gelezka
        if (partCode.getGelezkaPartNumber() != null) {
            System.out.println("partCode.getGelezkaPartNumber()!=null");
            for (int i = 0; i < codesList.size(); i++) {
                if (codesList.get(i).getGelezkaPartNumber() == null) {
                    continue;
                } else if (codesList.get(i).getGelezkaPartNumber().equals(partCode.getGelezkaPartNumber())) {
                    codeDates_0bollean_1string.add(true);
                    codeDates_0bollean_1string.add("gelezka code: " + partCode.getGelezkaPartNumber() + " exists in database");
                    return codeDates_0bollean_1string;
                }
            }
        }
        codeDates_0bollean_1string.add(false);
        codeDates_0bollean_1string.add("совпадений нет");
        return codeDates_0bollean_1string;
    }

    @Override
    public PartCode findById(Long id) {
        Optional<PartCode> optional = partCodeRepository.findById(id);
        PartCode partCode = null;
        if (optional.isPresent()) {
            partCode = optional.get();
        } else {
            throw new RuntimeException("FunDB not found for id :: " + id);
        }
        return partCode;
    }

    @Override
    public void save(PartCode partCode) {
        partCodeRepository.save(partCode);
    }

    @Override
    public void rememberObjectCode(Long id) {
        if (this.partCode == null) this.partCode = new PartCode();
        this.partCode = findById(id);
    }

    @Override
    public PartCode getRememberedObjectCode() {
        if (this.partCode == null) {
            System.out.println("Object is null");
            throw new NullPointerException("Object is null");
        }
        return this.partCode;
    }

    @Override
    public void clearRememberedObjectCode() {
        this.partCode = null;
    }

    @Override
    public List<PartCode> findAllTextFields(String serchingText) {
        if (serchingText == null) {
            System.out.println("нет данных для поиска");
            serchingText = " ";
        }
        String textInLowerCase = serchingText.toLowerCase();
        List<PartCode> findingList = new ArrayList<>();

        for (PartCode code : partCodeRepository.findAll())
            if ((code.getOriginalPartNumber() != null && code.getOriginalPartNumber().toLowerCase().contains(textInLowerCase)) |
                    (code.getGelezkaPartNumber() != null && code.getGelezkaPartNumber().toLowerCase().contains(textInLowerCase)) |
                    (code.getBrinePartNumber() != null && code.getBrinePartNumber().toLowerCase().contains(textInLowerCase))
            ) findingList.add(code);
        return findingList;
    }
}
