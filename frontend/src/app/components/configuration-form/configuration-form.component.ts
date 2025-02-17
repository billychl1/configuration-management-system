import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Configuration } from '../../models/configuration';

/**
 * Component for creating and editing configurations.
 * Provides a form dialog for configuration management.
 */
@Component({
  selector: 'app-configuration-form',
  templateUrl: './configuration-form.component.html',
  styleUrls: ['./configuration-form.component.css']
})
export class ConfigurationFormComponent implements OnInit {
  /** Form group for the configuration form */
  form!: FormGroup;
  
  /** Flag indicating if the form is in edit mode */
  isEditMode: boolean;

  constructor(
    private formBuilder: FormBuilder,
    private dialogRef: MatDialogRef<ConfigurationFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Configuration
  ) {
    this.isEditMode = !!data;
  }

  /**
   * Initializes the form with configuration data if in edit mode.
   * Disables the key field in edit mode.
   */
  ngOnInit() {
    this.form = this.formBuilder.group({
      id: [this.data?.id],
      key: [this.data?.key || '', [Validators.required]],
      value: [this.data?.value || '', [Validators.required]],
      description: [this.data?.description || '']
    });

    if (this.isEditMode) {
      this.form.get('key')?.disable();
    }
  }

  /**
   * Handles form submission.
   * Closes the dialog with form data if valid.
   */
  onSubmit() {
    if (this.form.valid) {
      const formValue = this.form.getRawValue();
      this.dialogRef.close(formValue);
    }
  }

  /**
   * Handles dialog cancellation.
   * Closes the dialog without saving.
   */
  onCancel() {
    this.dialogRef.close();
  }
}