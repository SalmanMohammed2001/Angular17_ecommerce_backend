package com.lk.ecommerce.service.admin.faq;

import com.lk.ecommerce.dto.core.FAQDto;
import com.lk.ecommerce.entity.FAQ;
import com.lk.ecommerce.entity.Product;
import com.lk.ecommerce.repo.FaqRepository;
import com.lk.ecommerce.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FAQServiceImpl implements FAQService{

    private final FaqRepository faqRepository;

    private final ProductRepository productRepository;

    public FAQDto postFAQ(UUID productId, FAQDto faqDto){
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if(optionalProduct.isPresent()){
            FAQ faq = new FAQ();
            faq.setQuestion(faqDto.getQuestion());
            faq.setAnswer(faqDto.getAnswer());
            faq.setProduct(optionalProduct.get());
            return faqRepository.save(faq).getFAQDto();
        }
        return  null;
    }
}
