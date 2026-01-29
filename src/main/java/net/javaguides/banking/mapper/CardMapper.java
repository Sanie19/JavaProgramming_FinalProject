package net.javaguides.banking.mapper;

import net.javaguides.banking.dto.CardDto;
import net.javaguides.banking.entity.Card;

public class CardMapper {

    public static CardDto mapToCardDto(Card c){
        return new CardDto(
                c.getId(),
                c.getCardNumber(),
                c.getCardHolderName(),
                c.getExpiryDate(),
                c.getAccount().getId()
        );
    }
}
