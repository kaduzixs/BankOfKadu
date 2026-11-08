package banck.client.kadu.DTO;

import java.math.BigDecimal;

public record AccountResponse(Long id, String accountNumber, BigDecimal balance){}