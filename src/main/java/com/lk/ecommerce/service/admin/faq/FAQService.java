package com.lk.ecommerce.service.admin.faq;

import com.lk.ecommerce.dto.core.FAQDto;


import java.util.UUID;

public interface FAQService {
    public FAQDto postFAQ(UUID productId, FAQDto faqDto);
}
