import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule } from '@angular/material/dialog';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';

import { AppComponent } from './app.component';

import { AuthenticationService } from './services/authentication.services';
import { RouterService } from './services/router.services';
import { GipherService } from 'src/app/services/gipher.services';
import { MatBadgeModule, MatSnackBarModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { LoginComponent } from 'src/app/login/login.component';
import { RegisterComponent } from 'src/app/register/register.component';
import { DashboardComponent } from 'src/app/dashboard/dashboard.component';
import { BookmarkComponent } from 'src/app/bookmark/bookmark.component';
import { RecommendComponent } from 'src/app/recommend/recommend.component';
import { HeaderComponent } from 'src/app/header/header.component';
import { CardComponent } from 'src/app/card/card.component';
import { DialogComponent } from 'src/app/dialog/dialog.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'dashboard',
    component: DashboardComponent
  },
  {
    path: 'bookmark',
    component: BookmarkComponent
  },
  {
    path: 'recommend',
    component: RecommendComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    DashboardComponent,
    HeaderComponent,
    CardComponent,
    DialogComponent,
    BookmarkComponent,
    RecommendComponent
  ],
  imports: [
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(routes),
    MatToolbarModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatDialogModule,
    MatOptionModule,
    MatSelectModule,
    MatIconModule,
    MatBadgeModule,
    MatSnackBarModule
  ],
  providers: [
    AuthenticationService,
    RouterService,
    GipherService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
