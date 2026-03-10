/**
 * Defines the structure of a Student record.
 * Used across StudentService and DashboardComponent.
 */
export interface Student {
  regNo: string;
  rollNo: string;
  name: string;
  standard: string;
  school: string;
}

/**
 * Defines the allowed roles for the application.
 * ADMIN: Can Create, Update, and Delete.
 * STAFF: Can only Read/View.
 * null: Used for unauthenticated state.
 */
export type UserRole = 'ADMIN' | 'STAFF' | null;