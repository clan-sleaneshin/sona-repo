import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class SworkerComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-sworker div table .btn-danger'));
  title = element.all(by.css('jhi-sworker div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getText();
  }
}

export class SworkerUpdatePage {
  pageTitle = element(by.id('jhi-sworker-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  nationalityInput = element(by.id('field_nationality'));
  birthDateInput = element(by.id('field_birthDate'));
  heightInput = element(by.id('field_height'));
  hairColorInput = element(by.id('field_hairColor'));
  eyeColorInput = element(by.id('field_eyeColor'));
  ethnicityInput = element(by.id('field_ethnicity'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setNationalityInput(nationality) {
    await this.nationalityInput.sendKeys(nationality);
  }

  async getNationalityInput() {
    return await this.nationalityInput.getAttribute('value');
  }

  async setBirthDateInput(birthDate) {
    await this.birthDateInput.sendKeys(birthDate);
  }

  async getBirthDateInput() {
    return await this.birthDateInput.getAttribute('value');
  }

  async setHeightInput(height) {
    await this.heightInput.sendKeys(height);
  }

  async getHeightInput() {
    return await this.heightInput.getAttribute('value');
  }

  async setHairColorInput(hairColor) {
    await this.hairColorInput.sendKeys(hairColor);
  }

  async getHairColorInput() {
    return await this.hairColorInput.getAttribute('value');
  }

  async setEyeColorInput(eyeColor) {
    await this.eyeColorInput.sendKeys(eyeColor);
  }

  async getEyeColorInput() {
    return await this.eyeColorInput.getAttribute('value');
  }

  async setEthnicityInput(ethnicity) {
    await this.ethnicityInput.sendKeys(ethnicity);
  }

  async getEthnicityInput() {
    return await this.ethnicityInput.getAttribute('value');
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class SworkerDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-sworker-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-sworker'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
