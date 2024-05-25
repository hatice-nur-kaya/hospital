package com.kodhnk.base.services.concretes;

import com.kodhnk.base.core.constant.Response;
import com.kodhnk.base.core.utilities.DataResult;
import com.kodhnk.base.core.utilities.ErrorDataResult;
import com.kodhnk.base.core.utilities.Result;
import com.kodhnk.base.core.utilities.SuccessDataResult;
import com.kodhnk.base.dataAccess.ExaminationRepository;
import com.kodhnk.base.dto.examinations.CreateExaminationRequest;
import com.kodhnk.base.dto.examinations.UpdateExaminationRequest;
import com.kodhnk.base.entities.Examination;
import com.kodhnk.base.services.interfaces.IExaminationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExaminationService implements IExaminationService {

    private final ExaminationRepository examinationRepository;

    public ExaminationService(ExaminationRepository examinationRepository) {
        this.examinationRepository = examinationRepository;
    }

    @Override
    public DataResult<List<Examination>> getAllExaminations(Long hospitalId) {
        return null;
    }

    @Override
    public DataResult<Examination> getExaminationById(Long id) {
        Optional<Examination> examination = examinationRepository.findById(id);
        if (examination.isPresent()) {
            return new SuccessDataResult<>(Response.GET_EXAMINATION.getMessage(), examination.get(), 200);
        } else {
            return new ErrorDataResult<>(Response.EXAMINATION_NOT_FOUND.getMessage(), null, 200);
        }
    }

    @Override
    public DataResult<Examination> createExamination(CreateExaminationRequest request) {
        return null;
    }

    @Override
    public DataResult<Examination> updateExamination(UpdateExaminationRequest request) {
        return null;
    }

    @Override
    public Result deletedeleteExamination(Long id) {
        return null;
    }
}