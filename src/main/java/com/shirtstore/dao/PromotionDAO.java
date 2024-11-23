package com.shirtstore.dao;

import com.shirtstore.entity.Promotion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromotionDAO extends JPADAO<Promotion> implements GenericDAO<Promotion> {

    public PromotionDAO() {
    }

    @Override
    public Promotion create(Promotion promotion) {
        return super.create(promotion);
    }

    @Override
    public Promotion update(Promotion promotion) {
        return super.update(promotion);
    }

    @Override
    public Promotion get(Object promotionId) {
        return super.find(Promotion.class, promotionId);
    }

    @Override
    public void delete(Object id) {

    }

    @Override
    public List<Promotion> listAll() {
        return super.findWithNamedQuery("Promotion.findAll");
    }

    @Override
    public long count() {
        return super.countWithNamedQuery("Promotion.countAll");
    }

    public List<Long> countUsedAllPromotion() {
        return super.countListWithNamedQuery3("Promotion.countUsedAllPromotionId");
    }

    public long countUsedPromotion(String promotionId) {
        return super.countWithNamedQuery("Promotion.countUsedPromotionId", "promotionId", promotionId);
    }

    public Promotion findByName(String promotionID) {
        List<Promotion> promotion = super.findWithNamedQuery("Promotion.findByPromotionId", "promotionId", promotionID);

        if(promotion != null && promotion.size() > 0) {
            return promotion.get(0);
        }
        return null;
    }

    public Map<String, String> findPromotionsBeingDisplayed(String promotionType){
        List<Object[]> listPromotions = super.findWithNamedQueryObjects("Promotion.findPromotionsBeingDisplayed");
        Map<String, String> res = new HashMap<String, String>();
        String promotionId, promotionDescription, type;

        for(Object[] promotion : listPromotions) {
            promotionId = (String) promotion[0];
            promotionDescription = (String) promotion[1];
            type = (String) promotion[2];

            if(promotionType.equals(type)){
                res.put(promotionId, promotionDescription);
            }
        }

        return res;
    }

    public Map<String, String> findOrderPromotionsBeingDisplayed(){
        return findPromotionsBeingDisplayed("Order Discount");
    }

    public Map<String, String> findShippingPromotionsBeingDisplayed(){
        return findPromotionsBeingDisplayed("Shipping Discount");
    }
}
