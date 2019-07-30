/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SworkerComponentsPage, SworkerDeleteDialog, SworkerUpdatePage } from './sworker.page-object';

const expect = chai.expect;

describe('Sworker e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let sworkerUpdatePage: SworkerUpdatePage;
  let sworkerComponentsPage: SworkerComponentsPage;
  let sworkerDeleteDialog: SworkerDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Sworkers', async () => {
    await navBarPage.goToEntity('sworker');
    sworkerComponentsPage = new SworkerComponentsPage();
    await browser.wait(ec.visibilityOf(sworkerComponentsPage.title), 5000);
    expect(await sworkerComponentsPage.getTitle()).to.eq('Sworkers');
  });

  it('should load create Sworker page', async () => {
    await sworkerComponentsPage.clickOnCreateButton();
    sworkerUpdatePage = new SworkerUpdatePage();
    expect(await sworkerUpdatePage.getPageTitle()).to.eq('Create or edit a Sworker');
    await sworkerUpdatePage.cancel();
  });

  it('should create and save Sworkers', async () => {
    const nbButtonsBeforeCreate = await sworkerComponentsPage.countDeleteButtons();

    await sworkerComponentsPage.clickOnCreateButton();
    await promise.all([
      sworkerUpdatePage.setNameInput('name'),
      sworkerUpdatePage.setNationalityInput('nationality'),
      sworkerUpdatePage.setBirthDateInput('2000-12-31'),
      sworkerUpdatePage.setHeightInput('5'),
      sworkerUpdatePage.setHairColorInput('hairColor'),
      sworkerUpdatePage.setEyeColorInput('eyeColor'),
      sworkerUpdatePage.setEthnicityInput('ethnicity')
    ]);
    expect(await sworkerUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await sworkerUpdatePage.getNationalityInput()).to.eq('nationality', 'Expected Nationality value to be equals to nationality');
    expect(await sworkerUpdatePage.getBirthDateInput()).to.eq('2000-12-31', 'Expected birthDate value to be equals to 2000-12-31');
    expect(await sworkerUpdatePage.getHeightInput()).to.eq('5', 'Expected height value to be equals to 5');
    expect(await sworkerUpdatePage.getHairColorInput()).to.eq('hairColor', 'Expected HairColor value to be equals to hairColor');
    expect(await sworkerUpdatePage.getEyeColorInput()).to.eq('eyeColor', 'Expected EyeColor value to be equals to eyeColor');
    expect(await sworkerUpdatePage.getEthnicityInput()).to.eq('ethnicity', 'Expected Ethnicity value to be equals to ethnicity');
    await sworkerUpdatePage.save();
    expect(await sworkerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await sworkerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Sworker', async () => {
    const nbButtonsBeforeDelete = await sworkerComponentsPage.countDeleteButtons();
    await sworkerComponentsPage.clickOnLastDeleteButton();

    sworkerDeleteDialog = new SworkerDeleteDialog();
    expect(await sworkerDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Sworker?');
    await sworkerDeleteDialog.clickOnConfirmButton();

    expect(await sworkerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
