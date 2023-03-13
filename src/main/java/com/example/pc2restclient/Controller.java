package com.example.pc2restclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    @Autowired ConnectionService connectionService;
    @Autowired
    PurchaseRepository purchaseRepository;
    @GetMapping("")
    public String defaulPage(){return "working ok!";}

    @GetMapping("/check/{id}")
    public String checkInventory(@PathVariable("id")
                                 final String id){
        return connectionService.checkInventoryService(id);
    }

    @GetMapping("/lock/{id}")
    public String lockInventory(@PathVariable("id")
                                 final String id){
        return connectionService.lockInventoryService(id);
    }

    @GetMapping("/unlock/{id}")
    public String unlockInventory(@PathVariable("id")
                                final String id){
        return connectionService.unlockInventoryService(id);
    }

    @GetMapping("/order/create/{id}")
    public String createOrderForInventoryId(@PathVariable("id")
                                            final Integer id)
    {
        Purchase purchase = new Purchase();
        purchase.setInventory(id);
        purchase.setName("admin");
        purchase.setNumber(1);
        purchase.setLock(false);
        purchaseRepository.save(purchase);
        return "created" + purchase.toString();
    }

    @GetMapping("/order/safecreate/{id}")
    public String safeXaCreateOrderForInventoryId(@PathVariable("id")
                                            final Integer id) throws Exception
    {
        String errorMessageForCommitStage ="";
        String stringId = id.toString();
        Purchase purchase = new Purchase();
        purchase.setInventory(id);
        purchase.setName("admin");
        purchase.setNumber(1);
        purchase.setLock(false);

        String prepareResult = connectionService.prepareAndLockInventory(stringId);
        if (!prepareResult.substring(0,4).equals("<200")) {
            throw new Exception();
        }
        try {
            errorMessageForCommitStage = connectionService.commitAndUnockInventory(stringId);
            purchaseRepository.save(purchase);
        }catch (Exception e){
            System.out.println("Rolling back inventory with id" + stringId);
            String response = connectionService.rollback(stringId);
            return response;
        }
        return "created" + purchase.toString();
    }

    @GetMapping("/order/list")
    public List<Purchase> getOrderList(){
        return purchaseRepository.findAll();
    }

}
